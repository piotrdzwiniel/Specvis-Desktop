import pandas as pd


def process_session_files(session_info_file_path: str, session_data_file_path: str):
    """
    Process session files and convert them to CSV format.

    Parameters:
    session_info_file_path (str): File path of session info file.
    session_data_file_path (str): File path of session data file.

    Columns of session_info DataFrame:
    - Parameter (str): Specvis procedure parameter name.
    - Value (str): Parameter's value.

    Columns of session_data DataFrame:
    - Index (int): Index/number of the stimuli location.
    - PosPxX (int): The position of the visual stimulus presented on the screen on the X-axis, expressed in pixels.
    - PosPxY (int): The position of the visual stimulus presented on the screen on the Y-axis, expressed in pixels.
    - DistDegFromFixPointX (float): The distance of the visual stimulus from the fixation point on the X-axis, in degrees.
    - DistDegFromFixPointY (float): The distance of the visual stimulus from the fixation point on the Y-axis, in degrees.
    - ThresholdBrightness (float): Recorded brightness threshold of the visual stimulus.
    - ThresholdLuminance (float): Recorded luminance threshold of the visual stimulus.
    - ThresholdDecibel (float): Recorded decibel threshold of the visual stimulus.

    Indexing stimuli rule:
    03 01 | 05 07
    02 00 | 04 06
    -------------
    14 12 | 08 10
    15 13 | 09 11

    Position of stimuli:
    - The position of the visual stimulus is expressed in pixels and is counted from left to right (X axis) and top to bottom (Y axis).

    Distance from fixation point:
    - For the X axis, the values to the left of the fixation point take on negative values, and to the right take on positive values.
    - For the Y-axis, values above the fixation point take on negative values, and values below take on positive values.
    - Values are expressed in degrees.
    """

    # Manage session_info.txt file
    info_columns = ['Parameter', 'Value']
    df_session_info = pd.read_csv(session_info_file_path, sep=': ', names=info_columns,
                                  encoding='windows-1250', engine='python')
    df_session_info_filtered = df_session_info.drop([0, 29])
    df_session_info_filtered.to_csv(str(session_info_file_path.split(sep='.')[0] + '.csv'),
                                    index=None)

    # Manage session_data.txt
    data_columns = ['Index', 'PosPxX', 'PosPxY', 'DistDegFromFixPointX',
                    'DistDegFromFixPointY', 'ThresholdBrightness', 'ThresholdLuminance',
                    'ThresholdDecibel']
    df_session_data = pd.read_csv(session_data_file_path, sep='\t', names=data_columns)
    df_session_data.to_csv(str(session_data_file_path.split(sep='.')[0] + '.csv'), index=None)


# Example usage:
session_info_file_path = "session_info.txt"
session_data_file_path = "session_data.txt"
process_session_files(session_info_file_path, session_data_file_path)
